package com.example.fido.mock;

import org.junit.Test;
import org.junit.Before;
import org.junit.runner.RunWith;

import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.List;
import static org.assertj.core.api.Assertions.assertThat;

@RunWith( MockitoJUnitRunner.class )
public class Testing {
    private List< String > someList;

    @Before
    public void setUp () {
        System.out.println( "Before" );
        this.someList = List.of(
                "asdasd",
                "asdasd",
                "asdasd",
                "asdasd",
                "asdasd"
        );
    }

    @Test
    public void testInsertedValues () {
        Mockito.when( this.someList )
                .thenReturn( List.of( "asdasd" ) );

        assertThat( this.someList ).isNotNull();
        assertThat( this.someList ).isNotEmpty();
        assertThat( this.someList.size() ).isEqualTo( 5 );
    }
}
