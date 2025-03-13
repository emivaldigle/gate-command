package com.visp.gate_command.mapper;

import com.visp.gate_command.domain.dto.EntityDto;
import com.visp.gate_command.domain.dto.EventDto;
import com.visp.gate_command.domain.dto.PocDto;
import com.visp.gate_command.domain.entity.Entity;
import com.visp.gate_command.domain.entity.Event;
import com.visp.gate_command.domain.entity.Poc;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2025-03-13T01:35:29-0300",
    comments = "version: 1.6.3, compiler: IncrementalProcessingEnvironment from gradle-language-java-8.12.1.jar, environment: Java 21.0.6 (Ubuntu)"
)
@Component
public class EventMapperImpl implements EventMapper {

    @Override
    public Event toEntity(EventDto eventDto) {
        if ( eventDto == null ) {
            return null;
        }

        Event event = new Event();

        event.setId( eventDto.getId() );
        event.setPoc( pocDtoToPoc( eventDto.getPoc() ) );
        event.setPlate( eventDto.getPlate() );
        event.setType( eventDto.getType() );
        event.setCreatedAt( eventDto.getCreatedAt() );

        return event;
    }

    @Override
    public EventDto toDto(Event event) {
        if ( event == null ) {
            return null;
        }

        EventDto eventDto = new EventDto();

        eventDto.setId( event.getId() );
        eventDto.setPoc( pocToPocDto( event.getPoc() ) );
        eventDto.setPlate( event.getPlate() );
        eventDto.setType( event.getType() );
        eventDto.setCreatedAt( event.getCreatedAt() );

        return eventDto;
    }

    protected Entity entityDtoToEntity(EntityDto entityDto) {
        if ( entityDto == null ) {
            return null;
        }

        Entity.EntityBuilder entity = Entity.builder();

        entity.id( entityDto.getId() );
        entity.name( entityDto.getName() );
        entity.type( entityDto.getType() );
        entity.address( entityDto.getAddress() );
        entity.taxId( entityDto.getTaxId() );
        entity.contactPhone( entityDto.getContactPhone() );
        entity.region( entityDto.getRegion() );
        entity.city( entityDto.getCity() );
        entity.commune( entityDto.getCommune() );
        entity.syncIntervalMinutes( entityDto.getSyncIntervalMinutes() );
        entity.parkingHoursAllowed( entityDto.getParkingHoursAllowed() );

        return entity.build();
    }

    protected Poc pocDtoToPoc(PocDto pocDto) {
        if ( pocDto == null ) {
            return null;
        }

        Poc poc = new Poc();

        poc.setId( pocDto.getId() );
        poc.setIdentifier( pocDto.getIdentifier() );
        poc.setName( pocDto.getName() );
        poc.setType( pocDto.getType() );
        poc.setEntity( entityDtoToEntity( pocDto.getEntity() ) );
        poc.setLocation( pocDto.getLocation() );
        poc.setLastSync( pocDto.getLastSync() );
        poc.setCreatedAt( pocDto.getCreatedAt() );

        return poc;
    }

    protected EntityDto entityToEntityDto(Entity entity) {
        if ( entity == null ) {
            return null;
        }

        EntityDto entityDto = new EntityDto();

        entityDto.setId( entity.getId() );
        entityDto.setName( entity.getName() );
        entityDto.setType( entity.getType() );
        entityDto.setAddress( entity.getAddress() );
        entityDto.setRegion( entity.getRegion() );
        entityDto.setCity( entity.getCity() );
        entityDto.setCommune( entity.getCommune() );
        entityDto.setTaxId( entity.getTaxId() );
        entityDto.setContactPhone( entity.getContactPhone() );
        entityDto.setSyncIntervalMinutes( entity.getSyncIntervalMinutes() );
        entityDto.setParkingHoursAllowed( entity.getParkingHoursAllowed() );

        return entityDto;
    }

    protected PocDto pocToPocDto(Poc poc) {
        if ( poc == null ) {
            return null;
        }

        PocDto pocDto = new PocDto();

        pocDto.setId( poc.getId() );
        pocDto.setIdentifier( poc.getIdentifier() );
        pocDto.setName( poc.getName() );
        pocDto.setType( poc.getType() );
        pocDto.setEntity( entityToEntityDto( poc.getEntity() ) );
        pocDto.setLocation( poc.getLocation() );
        pocDto.setLastSync( poc.getLastSync() );
        pocDto.setCreatedAt( poc.getCreatedAt() );

        return pocDto;
    }
}
