// PartnerController.java
package com.ibmmq.backend.controller;

import com.ibmmq.backend.dto.PartnerDTO;
import com.ibmmq.backend.dto.PartnerRequest;
import com.ibmmq.backend.service.PartnerService;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

/**
 * Controller for handling partner-related requests.
 */
@RestController
@RequestMapping("/api/partners")
public class PartnerController {
    private final PartnerService partnerService;

    public PartnerController(PartnerService partnerService) {
        this.partnerService = partnerService;
    }

    /**
     * Endpoint to retrieve all partners with pagination.
     *
     * @param pageable the pagination information
     * @return a paginated list of partners
     */
    @GetMapping
    public ResponseEntity<Page<PartnerDTO>> getAllPartners(@PageableDefault(size = 20) Pageable pageable) {
        return ResponseEntity.ok(partnerService.getAllPartners(pageable));
    }

    /**
     * Endpoint to retrieve a partner by its ID.
     *
     * @param id the ID of the partner
     * @return the partner with the specified ID
     */
    @GetMapping("/{id}")
    public ResponseEntity<PartnerDTO> getPartnerById(@PathVariable Long id) {
        return ResponseEntity.ok(partnerService.getPartnerById(id));
    }

    /**
     * Endpoint to create a new partner.
     *
     * @param partnerRequest the request body containing partner details
     * @return the created partner
     */
    @PostMapping
    public ResponseEntity<PartnerDTO> createPartner(@Valid @RequestBody PartnerRequest partnerRequest) {
        return ResponseEntity.ok(partnerService.createPartner(partnerRequest));
    }

    /**
     * Endpoint to update an existing partner.
     *
     * @param id             the ID of the partner to update
     * @param partnerRequest the request body containing updated partner details
     * @return the updated partner
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePartner(@PathVariable Long id) {
        partnerService.deletePartner(id);
        return ResponseEntity.noContent().build();
    }
}