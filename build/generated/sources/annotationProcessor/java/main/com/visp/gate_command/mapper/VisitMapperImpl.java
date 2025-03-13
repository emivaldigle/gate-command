package com.visp.gate_command.mapper;

import com.visp.gate_command.domain.dto.EntityDto;
import com.visp.gate_command.domain.dto.UserDto;
import com.visp.gate_command.domain.dto.VisitDto;
import com.visp.gate_command.domain.entity.Entity;
import com.visp.gate_command.domain.entity.User;
import com.visp.gate_command.domain.entity.Visit;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2025-03-13T01:35:29-0300",
    comments = "version: 1.6.3, compiler: IncrementalProcessingEnvironment from gradle-language-java-8.12.1.jar, environment: Java 21.0.6 (Ubuntu)"
)
@Component
public class VisitMapperImpl implements VisitMapper {

    @Override
    public Visit toEntity(VisitDto visitDto) {
        if ( visitDto == null ) {
            return null;
        }

        Visit visit = new Visit();

        visit.setId( visitDto.getId() );
        visit.setResident( userDtoToUser( visitDto.getResident() ) );
        visit.setName( visitDto.getName() );
        visit.setLastName( visitDto.getLastName() );
        visit.setVehiclePlate( visitDto.getVehiclePlate() );
        visit.setExpirationDate( visitDto.getExpirationDate() );
        visit.setCreatedAt( visitDto.getCreatedAt() );

        return visit;
    }

    @Override
    public VisitDto toDto(Visit visit) {
        if ( visit == null ) {
            return null;
        }

        VisitDto visitDto = new VisitDto();

        visitDto.setId( visit.getId() );
        visitDto.setResident( userToUserDto( visit.getResident() ) );
        visitDto.setName( visit.getName() );
        visitDto.setLastName( visit.getLastName() );
        visitDto.setVehiclePlate( visit.getVehiclePlate() );
        visitDto.setExpirationDate( visit.getExpirationDate() );
        visitDto.setCreatedAt( visit.getCreatedAt() );

        return visitDto;
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

    protected User userDtoToUser(UserDto userDto) {
        if ( userDto == null ) {
            return null;
        }

        User user = new User();

        user.setId( userDto.getId() );
        user.setDocument( userDto.getDocument() );
        user.setName( userDto.getName() );
        user.setLastName( userDto.getLastName() );
        user.setPhoneNumber( userDto.getPhoneNumber() );
        user.setEmail( userDto.getEmail() );
        user.setPassword( userDto.getPassword() );
        user.setType( userDto.getType() );
        user.setEntity( entityDtoToEntity( userDto.getEntity() ) );
        user.setCreatedAt( userDto.getCreatedAt() );

        return user;
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

    protected UserDto userToUserDto(User user) {
        if ( user == null ) {
            return null;
        }

        UserDto userDto = new UserDto();

        userDto.setId( user.getId() );
        userDto.setDocument( user.getDocument() );
        userDto.setName( user.getName() );
        userDto.setLastName( user.getLastName() );
        userDto.setPhoneNumber( user.getPhoneNumber() );
        userDto.setEmail( user.getEmail() );
        userDto.setPassword( user.getPassword() );
        userDto.setType( user.getType() );
        userDto.setEntity( entityToEntityDto( user.getEntity() ) );
        userDto.setCreatedAt( user.getCreatedAt() );

        return userDto;
    }
}
