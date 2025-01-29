package com.devsuperior.demo.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import com.devsuperior.demo.dto.CityDTO;
import com.devsuperior.demo.entities.City;
import com.devsuperior.demo.exceptions.IntegrityViolationDataException;
import com.devsuperior.demo.exceptions.ResourceNotFoundException;
import com.devsuperior.demo.repositories.CityRepository;

@Service
public class CityService {
    
    @Autowired
    private CityRepository cityRepository;


    public List<CityDTO> findAll() {
        var cities = cityRepository.findAll();

        return cities.stream().map(c -> new CityDTO(c)).sorted((c1, c2) -> c1.getName().compareToIgnoreCase(c2.getName())).toList();
    }

    public CityDTO insert(CityDTO dto) {
        var entity = new City();
        entity.setName(dto.getName());

        entity = cityRepository.save(entity);

        return new CityDTO(entity);
    }

    public void delete(Long id) {
        if( !cityRepository.existsById(id) ) {
                throw new ResourceNotFoundException("Not found id: " +id);
        }
        try{

            cityRepository.deleteById(id);
        }
        catch (DataIntegrityViolationException e) {
            throw new IntegrityViolationDataException("Data integrity violation id");
        }
    }
}
