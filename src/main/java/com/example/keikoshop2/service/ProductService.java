package com.example.keikoshop2.service;


//Exception
import com.example.keikoshop2.exception.ProductNotFoundExeption;
import com.example.keikoshop2.exception.ProductAlreadyExistsExeption;
import com.example.keikoshop2.model.Cart;
import com.example.keikoshop2.repository.ProductRepository;
import com.example.keikoshop2.repository.CartRepository;

//Model
import com.example.keikoshop2.model.Product;

//Library
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.UUID;


@Service
@RequiredArgsConstructor
public class ProductService implements IProductService {
    private final ProductRepository productRepository;
    private final CartRepository cartRepository;
    private final String uploadDir = "src/main/resources/static/images/product/";

    @Override
    public List<Product> getAllProducts() {
        return productRepository.findAll();
    }



    @Override
    public Product createProduct(Product product, MultipartFile image) {
        if (productAlreadyExists(product.getName())) {
            throw new ProductAlreadyExistsExeption(product.getId() + " - " + product.getName() + " Already exists");
        }
        if (!image.isEmpty()) {
            try {
                String imageName = saveImage(image);
                product.setImage(imageName);
            } catch (IOException e) {
                throw new RuntimeException("Failed to save image", e);
            }
        }
        return productRepository.save(product);
    }

    private boolean productAlreadyExists(String name) {
        return productRepository.findByName(name).isPresent();
    }


    @Override
    public Product updateProduct(Product product, int id, MultipartFile image) {
        return productRepository.findById(id)
                .map(pr -> {
                    pr.setName(product.getName());
                    pr.setPrice(product.getPrice());
                    pr.setDescription(product.getDescription());
                    pr.setStock(product.getStock());
                    if (!image.isEmpty()) {
                        try {
                            // Delete the old image file
                            if (pr.getImage() != null) {
                                Path oldImagePath = Paths.get(uploadDir + pr.getImage());
                                Files.deleteIfExists(oldImagePath);
                            }

                            String imageName = saveImage(image);
                            pr.setImage(imageName);
                        } catch (IOException e) {
                            throw new RuntimeException("Failed to save image", e);
                        }
                    }
                    return productRepository.save(pr);
                }).orElseThrow(() -> new ProductNotFoundExeption("Product Not Found!"));
    }

    private String saveImage(MultipartFile file) throws IOException {
        if (file.isEmpty()) {
            throw new IOException("File is empty");
        }

        String mimeType = file.getContentType();
        if (mimeType == null || !mimeType.startsWith("image/")) {
            throw new IOException("Invalid file type. Only image files are allowed.");
        }

        String originalFileName = file.getOriginalFilename();
        String fileExtension = originalFileName.substring(originalFileName.lastIndexOf('.'));
        String uniqueFileName = UUID.randomUUID().toString() + fileExtension;

        Path path = Paths.get(uploadDir + uniqueFileName);
        Files.createDirectories(path.getParent());
        Files.write(path, file.getBytes());

        return uniqueFileName;
    }

    @Override
    public Product getProductById(int id) {
        return productRepository.findById(id)
                .orElseThrow(() -> new ProductNotFoundExeption("Product Not Found With Id: " + id));
    }


    @Override
    public void deleteProduct(int id) {
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new ProductNotFoundExeption("Product Not Found!"));

        // Delete the associated cart items
        List<Cart> cartItems = cartRepository.findByProductId(id);
        for (Cart cartItem : cartItems) {
            cartRepository.delete(cartItem);
        }

        // Delete the associated image file
        if (product.getImage() != null) {
            Path imagePath = Paths.get(uploadDir + product.getImage());
            try {
                Files.deleteIfExists(imagePath);
            } catch (IOException e) {
                throw new RuntimeException("Failed to delete image", e);
            }
        }

        productRepository.deleteById(id);
    }
}
