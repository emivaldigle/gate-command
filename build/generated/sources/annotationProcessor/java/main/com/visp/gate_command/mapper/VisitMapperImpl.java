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
    date = "2025-03-14T01:02:10-0300",
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
        entity.active( entityDto.isActive() );
        entity.createdAt( entityDto.getCreatedAt() );
        entity.lastUpdatedAt( entityDto.getLastUpdatedAt() );

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
        entityDto.active( entity.isActive() );
        entityDto.createdAt( entity.getCreatedAt() );
        entityDto.lastUpdatedAt( entity.getLastUpdatedAt() );

        return entityDto.build();
    }

    protected UserDto userToUserDto(User user) {
        if ( user == null ) {
            return null;
        }

        UserDto.UserDtoBuilder userDto = UserDto.builder();

        userDto.id( user.getId() );
        userDto.document( user.getDocument() );
        userDto.name( user.getName() );
        userDto.lastName( user.getLastName() );
        userDto.phoneNumber( user.getPhoneNumber() );
        userDto.email( user.getEmail() );
        userDto.password( user.getPassword() );
        userDto.type( user.getType() );
        userDto.entity( entityToEntityDto( user.getEntity() ) );
        userDto.createdAt( user.getCreatedAt() );

        return userDto.build();
    }
}
