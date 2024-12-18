package com.example.keikoshop2.service;

import com.example.keikoshop2.model.Voucher;
import com.example.keikoshop2.repository.VoucherRepository;
import org.springframework.stereotype.Service;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

@Service
public class VoucherService implements IVoucherService {
  @Autowired
  private VoucherRepository voucherRepository;

  @Override
  public List<Voucher> getAllVouchers() {
    return voucherRepository.findAll();
  }

  @Override
  public Voucher getVoucherById(int id) {
    return voucherRepository.findById(id).orElse(null);
  }

  @Override
  public Voucher getVoucherByCode(String code) {
    return voucherRepository.findByCode(code).orElse(null);
  }

  @Override
  public Voucher createVoucher(Voucher voucher) {
    // if voucher code exist
    if (voucherRepository.findByCode(voucher.getCode()).isPresent()) {
      return null;
    }
    return voucherRepository.save(voucher);
  }

  @Override
  public Voucher updateVoucher(int id, Voucher voucher) {
    // Check if the voucher with the given id exists
    if (voucherRepository.findById(id).isPresent()) {
      // Check if the voucher code is already used by another voucher
      Voucher existingVoucher = voucherRepository.findByCode(voucher.getCode()).orElse(null);
      if (existingVoucher != null && existingVoucher.getId() != id) {
        return null;
      }
      return voucherRepository.save(voucher);
    }
    return null;
  }

  @Override
  public void deleteVoucher(int id) {
    voucherRepository.deleteById(id);
  }
}
