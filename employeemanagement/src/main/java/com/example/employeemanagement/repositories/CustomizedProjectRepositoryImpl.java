package com.example.employeemanagement.repositories;

import java.util.List;

import com.example.employeemanagement.dto.ProjectName;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;

public class CustomizedProjectRepositoryImpl implements CustomizedProjectRepository{
    private final JdbcTemplate jdbc;

    public CustomizedProjectRepositoryImpl(JdbcTemplate jdbc) {
        this.jdbc = jdbc;
    }

    public List<ProjectName> getProjectNames() {
        String sql = "SELECT * FROM project";

        RowMapper<ProjectName> rowMapper = (r, i) -> {
            ProjectName proj = new ProjectName();
            proj.setProjectId(r.getString("project_id"));
            proj.setProjectCode(r.getString("project_code"));
            proj.setProjectName(r.getString("project_name"));
            return proj;
        };

        return jdbc.query(sql, rowMapper);
    }

    
}
