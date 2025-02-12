package lt.techin.movie_studio.dto;

import lt.techin.movie_studio.model.Screening;

import java.util.List;

public class ScreeningMapper {
    public static List<ScreeningDTO> toScreeningsDTOList(List<Screening> screenings) {
        List<ScreeningDTO> result = screenings.stream().map(ScreeningMapper::toScreeningDTO)
                .toList();

        return result;
    }

    public static ScreeningDTO toScreeningDTO(Screening screening) {
        return new ScreeningDTO(
                screening.getId(),
                screening.getHall(),
                screening.getScreeningTime()
        );
    }

    public static Screening toScreening(ScreeningDTO screeningDTO) {
        return new Screening(
                screeningDTO.id(),
                screeningDTO.hall(),
                screeningDTO.Screening_time()
        );
    }
}
