package TimetrackerApplication.example.TimetrackerApplication.Config;

import org.springframework.context.annotation.Bean;

public class ModelMapper {
    @Bean
    public ModelMapper modelMapper() {
        ModelMapper modelMapper = new ModelMapper();
        return modelMapper;
    }
}
