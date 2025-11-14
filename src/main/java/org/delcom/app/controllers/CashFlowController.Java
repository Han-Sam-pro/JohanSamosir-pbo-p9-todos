package org.delcom.todos.controllers;

import org.delcom.todos.dto.ApiResponse;
import org.delcom.todos.dto.CashFlowRequest;
import org.delcom.todos.entities.CashFlow;
import org.delcom.todos.services.ICashFlowService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/api/cash-flows")
public class CashFlowController {

    private final ICashFlowService cashFlowService;

    @Autowired
    public CashFlowController(ICashFlowService cashFlowService) {
        this.cashFlowService = cashFlowService;
    }

    // HANYA ADA SATU METODE addCashFlow DI SINI
    @PostMapping
    public ResponseEntity<ApiResponse> addCashFlow(@RequestBody CashFlowRequest request) {
        CashFlow newCashFlow = cashFlowService.addCashFlow(
                request.getType(), request.getSource(), request.getLabel(),
                request.getDescription(), request.getAmount());

        if (newCashFlow == null) {
            return new ResponseEntity<>(new ApiResponse("error", "Tipe atau sumber tidak valid", null), HttpStatus.BAD_REQUEST);
        }

        Map<String, String> data = new HashMap<>();
        data.put("id", newCashFlow.getId());
        return new ResponseEntity<>(new ApiResponse("success", "Berhasil menambahkan data", data), HttpStatus.CREATED);
    }
    @GetMapping
    public ResponseEntity<ApiResponse> getAllCashFlows(@RequestParam(required = false) String search) {
        Map<String, Object> data = new HashMap<>();
        data.put("cash_flows", cashFlowService.getAll(search));
        return ResponseEntity.ok(new ApiResponse("success", "Berhasil mengambil data", data));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse> getCashFlowById(@PathVariable String id) {
        Optional<CashFlow> cashFlowOpt = cashFlowService.getById(id);
        if (cashFlowOpt.isPresent()) {
            Map<String, Object> data = new HashMap<>();
            data.put("cash_flow", cashFlowOpt.get());
            return ResponseEntity.ok(new ApiResponse("success", "Berhasil mengambil data", data));
        }
        return new ResponseEntity<>(new ApiResponse("error", "Data tidak ditemukan", null), HttpStatus.NOT_FOUND);
    }

    @GetMapping("/labels")
    public ResponseEntity<ApiResponse> getLabels() {
        Map<String, Object> data = new HashMap<>();
        data.put("labels", cashFlowService.getAllLabels());
        return ResponseEntity.ok(new ApiResponse("success", "Berhasil mengambil data", data));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse> updateCashFlow(@PathVariable String id, @RequestBody CashFlowRequest request) {
        boolean updated = cashFlowService.updateCashFlow(id, request.getType(), request.getSource(),
                request.getLabel(), request.getDescription(), request.getAmount());
        if (updated) {
            return ResponseEntity.ok(new ApiResponse("success", "Berhasil memperbarui data", null));
        }
        return new ResponseEntity<>(new ApiResponse("error", "Gagal memperbarui data, ID tidak ditemukan atau input tidak valid", null), HttpStatus.NOT_FOUND);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse> deleteCashFlow(@PathVariable String id) {
        boolean deleted = cashFlowService.removeCashFlow(id);
        if (deleted) {
            return ResponseEntity.ok(new ApiResponse("success", "Berhasil menghapus data", null));
        }
        return new ResponseEntity<>(new ApiResponse("error", "Gagal menghapus data, ID tidak ditemukan", null), HttpStatus.NOT_FOUND);
    }
}