package com.devsuperior.demo.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.devsuperior.demo.dto.EventDTO;
import com.devsuperior.demo.entities.Event;
import com.devsuperior.demo.exceptions.ResourceNotFoundException;
import com.devsuperior.demo.repositories.CityRepository;
import com.devsuperior.demo.repositories.EventRepository;

@Service
public class EventService {
    
    @Autowired
    private EventRepository eventRepository;

    @Autowired
    private CityRepository cityRepository;

    public EventDTO update(Long id, EventDTO dto) {
        if( !eventRepository.existsById(id) ) {
                throw new ResourceNotFoundException("Not found id: " +id);
        }

        var city = cityRepository.getReferenceById(dto.getCityId());

        var entity = new Event();
        entity.setId(id);
        entity.setName(dto.getName());
        entity.setUrl(dto.getUrl());
        entity.setDate(dto.getDate());
        entity.setCity(city);
       
       entity = eventRepository.save(entity);
       
       return new EventDTO(entity);
    }
}
