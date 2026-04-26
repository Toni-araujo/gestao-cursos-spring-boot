package com.example.books;

import com.example.cursos.CursosApplication;
import org.springframework.boot.SpringApplication;

public class TestSpringJdbcBooksApplication {

    public static void main( String[] args )
    {
        SpringApplication.from( CursosApplication::main )
                         .with( TestcontainersConfiguration.class )
                         .run( args );
    }

}
