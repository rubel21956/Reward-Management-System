package net.spectrum.api.application.converter;

import net.spectrum.api.application.dto.*;
import net.spectrum.api.application.entity.ApplicationEntity;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.stereotype.Component;


@Component
public class ApplicationConverter {
  
    ModelMapper modelMapper = new ModelMapper();

    
    public ApplicationEntity applicationCustomsStepOneDtoToApplicationEntity(ApplicationCustomsStepOneDto applicationCustomsStepOneDto) {
        this.modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.LOOSE);
        return modelMapper.map(applicationCustomsStepOneDto ,ApplicationEntity.class);
   }

    public ApplicationEntity applicationCustomsStepTwoDtoToApplicationEntity(ApplicationCustomsStepTwoDto applicationCustomsStepTwoDto) {
        this.modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.LOOSE);
        return modelMapper.map(applicationCustomsStepTwoDto ,ApplicationEntity.class);
    }

    public ApplicationEntity applicationNbrAdminStepOneDtoToApplicationEntity(ApplicationNbrAdminStepOneDto applicationNbrAdminStepOneDto) {
        this.modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.LOOSE);
        return modelMapper.map(applicationNbrAdminStepOneDto ,ApplicationEntity.class);
    }

    public ApplicationEntity applicationNbrAdminStepTwoDtoToApplicationEntity(ApplicationNbrAdminStepTwoDto applicationNbrAdminStepTwoDto) {
        this.modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.LOOSE);
        return modelMapper.map(applicationNbrAdminStepTwoDto ,ApplicationEntity.class);
    }

    public ApplicationCustomsStepOneDto applicationEntityToApplicationCustomsStepOneDto(ApplicationEntity applicationEntity) {
        this.modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.LOOSE);
        return modelMapper.map(applicationEntity ,ApplicationCustomsStepOneDto.class);
    }

   /** S lint
    * public ApplicationCustomsStepTwoDto applicationEntityToApplicationCustomsStepTwoDto(
        ApplicationEntity ApplicationEntity) {
        this.modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.LOOSE);
        return modelMapper.map(ApplicationEntity ,ApplicationCustomsStepTwoDto.class);
    }
        Correction
    **/

   public ApplicationCustomsStepTwoDto applicationEntityToApplicationCustomsStepTwoDto(
           ApplicationEntity applicationEntity) {
       this.modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.LOOSE);
       return modelMapper.map(applicationEntity ,ApplicationCustomsStepTwoDto.class);
   }
    /** S lint
    public ApplicationCustomsStepFiveDto applicationEntityToApplicationCustomsStepFiveDto(
        ApplicationEntity ApplicationEntity) {
        this.modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.LOOSE);
        return modelMapper.map(ApplicationEntity ,ApplicationCustomsStepFiveDto.class);
    }

     Correction
     **/

    public ApplicationCustomsStepFiveDto applicationEntityToApplicationCustomsStepFiveDto(
            ApplicationEntity applicationEntity) {
        this.modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.LOOSE);
        return modelMapper.map(applicationEntity ,ApplicationCustomsStepFiveDto.class);
    }

    public ApplicationNbrAdminStepOneDto applicationEntityToApplicationNbrAdminStepOneDto(ApplicationEntity applicationEntity) {
        this.modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.LOOSE);
        return modelMapper.map(applicationEntity ,ApplicationNbrAdminStepOneDto.class);
    }

    public ApplicationNbrAdminStepTwoDto applicationEntityToApplicationNbrAdminStepTwoDto(ApplicationEntity applicationEntity) {
        this.modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.LOOSE);
        return modelMapper.map(applicationEntity ,ApplicationNbrAdminStepTwoDto.class);
    }

    public ApplicationNbrAdminStepThreeDto applicationEntityToApplicationNbrAdminStepThreeDto(ApplicationEntity applicationEntity) {
        this.modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.LOOSE);
        return modelMapper.map(applicationEntity ,ApplicationNbrAdminStepThreeDto.class);
    }
}
