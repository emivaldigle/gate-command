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
    date = "2025-03-15T11:26:56-0300",
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
        entity.visitSizeLimit( entityDto.getVisitSizeLimit() );
        entity.parkingSizeLimit( entityDto.getParkingSizeLimit() );
        entity.active( entityDto.isActive() );
        entity.createdAt( entityDto.getCreatedAt() );
        entity.lastUpdatedAt( entityDto.getLastUpdatedAt() );

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
        poc.setActive( pocDto.isActive() );

        return poc;
    }

    protected EntityDto entityToEntityDto(Entity entity) {
        if ( entity == null ) {
            return null;
        }

        EntityDto.EntityDtoBuilder entityDto = EntityDto.builder();

        entityDto.id( entity.getId() );
        entityDto.name( entity.getName() );
        entityDto.type( entity.getType() );
        entityDto.address( entity.getAddress() );
        entityDto.region( entity.getRegion() );
        entityDto.city( entity.getCity() );
        entityDto.commune( entity.getCommune() );
        entityDto.taxId( entity.getTaxId() );
        entityDto.contactPhone( entity.getContactPhone() );
        entityDto.syncIntervalMinutes( entity.getSyncIntervalMinutes() );
        entityDto.parkingHoursAllowed( entity.getParkingHoursAllowed() );
        entityDto.visitSizeLimit( entity.getVisitSizeLimit() );
        entityDto.parkingSizeLimit( entity.getParkingSizeLimit() );
        entityDto.active( entity.isActive() );
        entityDto.createdAt( entity.getCreatedAt() );
        entityDto.lastUpdatedAt( entity.getLastUpdatedAt() );

        return entityDto.build();
    }

    protected PocDto pocToPocDto(Poc poc) {
        if ( poc == null ) {
            return null;
        }

        PocDto.PocDtoBuilder pocDto = PocDto.builder();

        pocDto.id( poc.getId() );
        pocDto.identifier( poc.getIdentifier() );
        pocDto.name( poc.getName() );
        pocDto.type( poc.getType() );
        pocDto.entity( entityToEntityDto( poc.getEntity() ) );
        pocDto.location( poc.getLocation() );
        pocDto.lastSync( poc.getLastSync() );
        pocDto.createdAt( poc.getCreatedAt() );
        if ( poc.getActive() != null ) {
            pocDto.active( poc.getActive() );
        }

        return pocDto.build();
    }
}
