package com.df.luminor.iban.rest;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.df.luminor.iban.dao.entity.IbanDBVO;
import com.df.luminor.iban.dao.repository.IbanRepository;
import com.df.luminor.iban.dto.IbanDTO;
import com.df.luminor.iban.mapper.IbanDbvoToDtoMapper;
import com.df.luminor.iban.mapper.IbanDtoToDbvoMapper;
import com.df.luminor.iban.service.IbanValidatorService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;

@RestController
@RequestMapping("/api")
public class IbanRestService {

    @Autowired
    private IbanRepository ibanRepository;

    @Autowired
    private IbanValidatorService ibanValidatorService;


    @Operation(summary = "Get list of previously validated IBANs")
    @GetMapping(path = "/ibans", produces = "application/json")
    public List<IbanDTO> getIbans(
            @Parameter(name = "Predefined filtering rule")
            @RequestParam Filter filter) {

        Iterable<IbanDBVO> result = Collections.emptyList();
        switch (filter) {
            case INVALID:
                result = ibanRepository.findAllByValid(false);
                break;
            case VALID:
                result = ibanRepository.findAllByValid(true);
                break;
            case ALL:
            default:
                result = ibanRepository.findAll();
        }
        var collect = StreamSupport.stream(result.spliterator(), false)
                .map(IbanDbvoToDtoMapper.INSTANCE::dbvo2dto)
                .collect(Collectors.toList());
        return collect;
    }

    @Operation(summary = "Validate single or multiple IBANs")
    @PostMapping(path = "/ibans/validate", consumes = "application/json", produces = "application/json")
    public List<IbanDTO> validateIbans(
            @Parameter(name = "List of IBANs to validate")
            @RequestBody List<IbanDTO> ibans) {

        var collect = ibans.stream()
                .map(IbanDtoToDbvoMapper.INSTANCE::dto2dbvo)
                .map(ibanDBVO -> {
                    var foundInRepo = ibanRepository.findByCountryCodeAndCheckDigitsAndBban(
                            ibanDBVO.getCountryCode(),
                            ibanDBVO.getCheckDigits(),
                            ibanDBVO.getBban()
                    );
                    if (foundInRepo.isPresent()) {
                        ibanDBVO = foundInRepo.get();
                    } else {
                        ibanDBVO.setValid(ibanValidatorService.isValid(ibanDBVO));
                        ibanDBVO = ibanRepository.save(ibanDBVO);
                    }
                    return ibanDBVO;
                })
                .map(IbanDbvoToDtoMapper.INSTANCE::dbvo2dto)
                .collect(Collectors.toList());
        return collect;
    }

}
