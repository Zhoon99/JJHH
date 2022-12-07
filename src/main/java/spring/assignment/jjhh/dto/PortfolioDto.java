package spring.assignment.jjhh.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import spring.assignment.jjhh.entity.*;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

public class PortfolioDto {

    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Request {

        private String projectName;
        private String introduce;
        private String startDate;
        private String lastDate;
        private String readme;
        private String disclosure;
        private List<TechStackDto.Request> techStackList;
        private List<TeamDto.Request> teamList;

    }

    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Preview {

        private Long id;
        private String projectName;
        private String introduce;
        private String startDate;
        private String lastDate;
        private Integer views;

        private List<TechStack> techStackList;
        private List<Team> teamList;

    }

}
