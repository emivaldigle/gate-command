package com.visp.gate_command.mapper;

import com.visp.gate_command.domain.dto.EntityDto;
import com.visp.gate_command.domain.dto.UserDto;
import com.visp.gate_command.domain.dto.VehicleDto;
import com.visp.gate_command.domain.dto.VehicleSummaryDto;
import com.visp.gate_command.domain.entity.Entity;
import com.visp.gate_command.domain.entity.User;
import com.visp.gate_command.domain.entity.Vehicle;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2025-03-15T11:26:56-0300",
    comments = "version: 1.6.3, compiler: IncrementalProcessingEnvironment from gradle-language-java-8.12.1.jar, environment: Java 21.0.6 (Ubuntu)"
)
@Component
public class VehicleMapperImpl implements VehicleMapper {

    @Override
    public Vehicle toEntity(VehicleDto vehicleDto) {
        if ( vehicleDto == null ) {
            return null;
        }

        Vehicle vehicle = new Vehicle();

        vehicle.setId( vehicleDto.getId() );
        vehicle.setUser( userDtoToUser( vehicleDto.getUser() ) );
        vehicle.setPlate( vehicleDto.getPlate() );
        vehicle.setCreatedAt( vehicleDto.getCreatedAt() );
        vehicle.setVehicleType( vehicleDto.getVehicleType() );

        return vehicle;
    }

    @Override
    public VehicleDto toDto(Vehicle vehicle) {
        if ( vehicle == null ) {
            return null;
        }

        VehicleDto.VehicleDtoBuilder vehicleDto = VehicleDto.builder();

        vehicleDto.id( vehicle.getId() );
        vehicleDto.user( userToUserDto( vehicle.getUser() ) );
        vehicleDto.plate( vehicle.getPlate() );
        vehicleDto.createdAt( vehicle.getCreatedAt() );
        vehicleDto.vehicleType( vehicle.getVehicleType() );

        return vehicleDto.build();
    }

    @Override
    public VehicleSummaryDto toVehicleSummaryDto(Vehicle vehicle) {
        if ( vehicle == null ) {
            return null;
        }

        VehicleSummaryDto.VehicleSummaryDtoBuilder vehicleSummaryDto = VehicleSummaryDto.builder();

        vehicleSummaryDto.userId( vehicleUserId( vehicle ) );
        vehicleSummaryDto.plate( vehicle.getPlate() );

        vehicleSummaryDto.isVisit( vehicle.getUser().getType() == com.visp.gate_command.domain.enums.UserType.VISIT );

        return vehicleSummaryDto.build();
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
        user.setUnit( userDto.getUnit() );
        user.setType( userDto.getType() );
        user.setEntity( entityDtoToEntity( userDto.getEntity() ) );
        user.setHasAssignedParking( userDto.getHasAssignedParking() );
        user.setVisitDateTime( userDto.getVisitDateTime() );
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
        entityDto.visitSizeLimit( entity.getVisitSizeLimit() );
        entityDto.parkingSizeLimit( entity.getParkingSizeLimit() );
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
        userDto.unit( user.getUnit() );
        userDto.type( user.getType() );
        userDto.entity( entityToEntityDto( user.getEntity() ) );
        userDto.hasAssignedParking( user.getHasAssignedParking() );
        userDto.visitDateTime( user.getVisitDateTime() );
        userDto.createdAt( user.getCreatedAt() );

        return userDto.build();
    }

    private Long vehicleUserId(Vehicle vehicle) {
        User user = vehicle.getUser();
        if ( user == null ) {
            return null;
        }
        return user.getId();
    }
}
