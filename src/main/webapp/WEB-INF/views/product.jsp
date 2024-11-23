<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="id">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>KeikoShop - Product Management</title>
    <script src="https://cdn.tailwindcss.com"></script>
    <script src="https://cdnjs.cloudflare.com/ajax/libs/jquery/3.7.1/jquery.min.js"></script>
</head>
<body class="bg-gray-50">
<!-- Navigation -->
<nav class="bg-white shadow-lg">
    <div class="max-w-7xl mx-auto px-4">
        <div class="flex justify-between h-16">
            <div class="flex">
                <div class="flex-shrink-0 flex items-center">
                    <h1 class="text-xl font-bold">KeikoShop</h1>
                </div>
                <div class="hidden sm:ml-6 sm:flex sm:space-x-8">
                    <a href="/products" class="border-indigo-500 text-gray-900 inline-flex items-center px-1 pt-1 border-b-2 text-sm font-medium">
                        Products
                    </a>
                </div>
            </div>
        </div>
    </div>
</nav>

<!-- Main Content -->
<div class="max-w-7xl mx-auto py-6 sm:px-6 lg:px-8">
    <!-- Product List -->
    <div class="px-4 py-6 sm:px-0">
        <div class="flex justify-between items-center mb-6">
            <h2 class="text-2xl font-bold text-gray-900">Product Management</h2>
            <button onclick="openAddModal()" class="bg-indigo-600 text-white px-4 py-2 rounded-md hover:bg-indigo-700">
                Add New Product
            </button>
        </div>

        <!-- Product Table -->
        <div class="bg-white shadow overflow-hidden sm:rounded-md">
            <table class="min-w-full divide-y divide-gray-200">
                <thead class="bg-gray-50">
                <tr>
                    <th class="px-6 py-3 text-left text-xs font-medium text-gray-500 uppercase tracking-wider">ID</th>
                    <th class="px-6 py-3 text-left text-xs font-medium text-gray-500 uppercase tracking-wider">Product Name</th>
                    <th class="px-6 py-3 text-left text-xs font-medium text-gray-500 uppercase tracking-wider">Description</th>
                    <th class="px-6 py-3 text-left text-xs font-medium text-gray-500 uppercase tracking-wider">Price</th>
                    <th class="px-6 py-3 text-left text-xs font-medium text-gray-500 uppercase tracking-wider">Stock</th>
                    <th class="px-6 py-3 text-left text-xs font-medium text-gray-500 uppercase tracking-wider">Actions</th>
                </tr>
                </thead>
                <tbody class="bg-white divide-y divide-gray-200" id="productTableBody">
                <!-- Dynamic content will go here from backend -->
                <c:forEach var="product" items="${products}">
                    <tr>
                        <td class="px-6 py-4 whitespace-nowrap">${product.id}</td>
                        <td class="px-6 py-4 whitespace-nowrap">${product.name}</td>
                        <td class="px-6 py-4">${product.description != null ? product.description : '-'}</td>
                        <td class="px-6 py-4 whitespace-nowrap">Rp ${product.price}</td>
                        <td class="px-6 py-4 whitespace-nowrap">${product.stock}</td>
                        <td class="px-6 py-4 whitespace-nowrap">
                            <a href="edit/${product.id}" class="text-indigo-600 hover:text-indigo-900 mr-3">Edit</a>
                            <a href="delete/${product.id}" class="text-red-600 hover:text-red-900">Delete</a>
                        </td>
                    </tr>
                </c:forEach>
                </tbody>
            </table>
        </div>
    </div>
</div>

<!-- Add/Edit Product Modal -->
<div id="productModal" class="fixed inset-0 bg-gray-500 bg-opacity-75 hidden">
    <div class="flex items-center justify-center min-h-screen">
        <div class="bg-white rounded-lg p-8 max-w-md w-full">
            <h3 class="text-lg font-medium mb-4" id="modalTitle">Add New Product</h3>
            <form id="productForm" action="<%= request.getAttribute("productId") != null ? "/products/edit/" + request.getAttribute("productId") : "/products" %>" method="post" class="space-y-4">
                <input type="hidden" id="productId" name="productId" value="<%= request.getAttribute("productId") != null ? request.getAttribute("productId") : "" %>">
                <div>
                    <label class="block text-sm font-medium text-gray-700">Product Name</label>
                    <input type="text" id="productName" name="name" value="<%= request.getAttribute("productName") != null ? request.getAttribute("productName") : "" %>" class="mt-1 block w-full rounded-md border-gray-300 shadow-sm focus:border-indigo-500 focus:ring-indigo-500">
                </div>
                <div>
                    <label class="block text-sm font-medium text-gray-700">Description</label>
                    <textarea id="productDescription" name="description" class="mt-1 block w-full rounded-md border-gray-300 shadow-sm focus:border-indigo-500 focus:ring-indigo-500"><%= request.getAttribute("productDescription") != null ? request.getAttribute("productDescription") : "" %></textarea>
                </div>
                <div>
                    <label class="block text-sm font-medium text-gray-700">Price</label>
                    <input type="number" id="productPrice" name="price" value="<%= request.getAttribute("productPrice") != null ? request.getAttribute("productPrice") : "" %>" class="mt-1 block w-full rounded-md border-gray-300 shadow-sm focus:border-indigo-500 focus:ring-indigo-500">
                </div>
                <div>
                    <label class="block text-sm font-medium text-gray-700">Stock</label>
                    <input type="number" id="productStock" name="stock" value="<%= request.getAttribute("productStock") != null ? request.getAttribute("productStock") : "" %>" class="mt-1 block w-full rounded-md border-gray-300 shadow-sm focus:border-indigo-500 focus:ring-indigo-500">
                </div>
                <div class="flex justify-end space-x-3">
                    <button type="button" onclick="closeModal()" class="bg-gray-200 px-4 py-2 rounded-md hover:bg-gray-300">Cancel</button>
                    <button type="submit" class="bg-indigo-600 text-white px-4 py-2 rounded-md hover:bg-indigo-700">Save</button>
                </div>
            </form>
        </div>
    </div>
</div>

<script>
    // Function to open modal for adding new product
    function openAddModal() {
        document.getElementById('modalTitle').textContent = 'Add New Product';
        document.getElementById('productModal').classList.remove('hidden');
    }

    // Function to close modal
    function closeModal() {
        document.getElementById('productModal').classList.add('hidden');
    }
</script>

</body>
</html>
