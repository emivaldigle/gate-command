package com.visp.gate_command.mapper;

import com.visp.gate_command.domain.dto.EntityDto;
import com.visp.gate_command.domain.entity.Entity;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2025-03-13T01:35:29-0300",
    comments = "version: 1.6.3, compiler: IncrementalProcessingEnvironment from gradle-language-java-8.12.1.jar, environment: Java 21.0.6 (Ubuntu)"
)
@Component
public class EntityMapperImpl implements EntityMapper {

    @Override
    public Entity toEntity(EntityDto entityDto) {
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

    @Override
    public EntityDto toDto(Entity entity) {
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
}
