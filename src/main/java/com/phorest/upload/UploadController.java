package com.phorest.upload;

import com.phorest.appointment.AppointmentService;
import com.phorest.data.*;
import com.phorest.client.ClientService;
import com.phorest.product.ProductService;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.io.IOException;
import java.util.List;

import static com.phorest.PhorestConstants.HEADER_IMPORT_TYPE;
import static org.springframework.http.MediaType.MULTIPART_FORM_DATA_VALUE;

@RestController
@RequestMapping("api/v1")
@RequiredArgsConstructor
public class UploadController {
    private final UploadService uploadService;
    private final ClientService clientService;
    private final AppointmentService appointmentService;
    private final ProductService productService;

    @PostMapping(path = "/upload", consumes = MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<Void> upload(
            @Valid @RequestPart(value = "file", required = false)
            @NonNull MultipartFile file,
            @RequestHeader(HEADER_IMPORT_TYPE) String importType
    ) throws IOException {
        var csvData = file.getInputStream();
        var savedDataList = uploadService.upload(csvData, importType);

        if(!savedDataList.isEmpty() && savedDataList.get(0) instanceof Client){
            clientService.save((List<Client>)savedDataList);
            return ResponseEntity.created(null).build();
        }
        if(!savedDataList.isEmpty() && savedDataList.get(0) instanceof Appointment){
            appointmentService.save((List<Appointment>)savedDataList);
            return ResponseEntity.created(null).build();
        }
        if(!savedDataList.isEmpty() && savedDataList.get(0) instanceof PurchaseProduct){
            productService.savePurchases((List<Product>)savedDataList);
            return ResponseEntity.created(null).build();
        }
        if(!savedDataList.isEmpty() && savedDataList.get(0) instanceof ServiceProduct){
            productService.saveServices((List<Product>)savedDataList);
            return ResponseEntity.created(null).build();
        }
        return ResponseEntity.created(null).build();
    }

}
