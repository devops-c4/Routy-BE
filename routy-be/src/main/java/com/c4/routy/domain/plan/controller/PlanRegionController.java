//package com.c4.routy.domain.plan.controller;
//
//import lombok.RequiredArgsConstructor;
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RequestParam;
//import org.springframework.web.bind.annotation.RestController;
//
//@RestController
//@RequestMapping("/api/region")
//@RequiredArgsConstructor
//public class PlanRegionController {
//
//    private final RegionService regionService;
//
//    @GetMapping
//    public ResponseEntity<List<RegionDTO>> getRegions(
//            @RequestParam(required = false) String parentName) {
//        List<RegionDTO> regions = regionService.getRegions(parentName);
//        return ResponseEntity.ok(regions);
//    }
//}
