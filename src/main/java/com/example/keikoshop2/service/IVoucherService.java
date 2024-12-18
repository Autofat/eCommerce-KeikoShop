package com.example.keikoshop2.service;

import com.example.keikoshop2.model.Voucher;

import java.util.List;

public interface IVoucherService {
  List<Voucher> getAllVouchers();

  Voucher getVoucherById(int id);

  Voucher getVoucherByCode(String code);

  Voucher createVoucher(Voucher voucher);

  Voucher updateVoucher(int id, Voucher voucher);

  void deleteVoucher(int id);
}