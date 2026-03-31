package it.tabacchi.notifica;

import org.mapstruct.Mapper;
import java.util.List;

@Mapper(componentModel = "spring")
public interface NotificaMapper {

    NotificaDto toDto(Notifica notifica);
    
    List<NotificaDto> toDtoList(List<Notifica> notifiche);

    Notifica toEntity(NotificaDto dto);

}
