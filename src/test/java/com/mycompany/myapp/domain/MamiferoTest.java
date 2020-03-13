package com.mycompany.myapp.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.mycompany.myapp.web.rest.TestUtil;

public class MamiferoTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Mamifero.class);
        Mamifero mamifero1 = new Mamifero();
        mamifero1.setId(1L);
        Mamifero mamifero2 = new Mamifero();
        mamifero2.setId(mamifero1.getId());
        assertThat(mamifero1).isEqualTo(mamifero2);
        mamifero2.setId(2L);
        assertThat(mamifero1).isNotEqualTo(mamifero2);
        mamifero1.setId(null);
        assertThat(mamifero1).isNotEqualTo(mamifero2);
    }
}
