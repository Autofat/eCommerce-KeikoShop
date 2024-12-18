package com.example.keikoshop2.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import com.midtrans.httpclient.CoreApi;
import com.midtrans.httpclient.error.MidtransError;
import com.midtrans.Midtrans;
import java.util.HashMap;
import java.util.Map;

@Service
public class MidtransService {
  // private final CoreApi coreApi;

  // public MidtransService(@Value("${midtrans.server-key}") String serverKey,
  // @Value("${midtrans.is-production}") boolean isProduction) {
  // Config config = Config.builder()
  // .setServerKey(serverKey)
  // .setIsProduction(isProduction)
  // .build();
  // this.coreApi = new CoreApi(config);
  // }

  // public String createQrisPayment(Transactions transaction) throws Exception {
  // Map<String, Object> params = new HashMap<>();
  // params.put("payment_type", "qris");
  // params.put("transaction_details", new HashMap<String, Object>() {
  // {
  // put("order_id", "TRX-" + transaction.getId());
  // put("gross_amount", (int) transaction.getTotalPrice());
  // }
  // });

  // var response = coreApi.chargeTransaction(params);
  // String qrisUrl = response.getQrisUrl();
  // transaction.setMidtransTransactionId(response.getTransactionId());
  // return qrisUrl;
  // }
}