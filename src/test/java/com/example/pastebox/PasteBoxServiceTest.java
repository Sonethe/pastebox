package com.example.pastebox;

import com.example.pastebox.api.response.PasteBoxResponse;
import com.example.pastebox.exception.NotFoundEntityException;
import com.example.pastebox.model.PasteBox;
import com.example.pastebox.repository.PasteBoxRepository;
import com.example.pastebox.repository.PasteBoxRepositoryMap;
import com.example.pastebox.service.PasteBoxService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

@SpringBootTest
public class PasteBoxServiceTest {
    @Autowired
    private PasteBoxService pasteBoxService;

    @MockBean
    private PasteBoxRepositoryMap pasteBoxRepository;

    //выкидывает исключение при несуществующем хэше
    @Test
    public void notExistHash() {
        when(pasteBoxRepository.getByHash(anyString())).thenThrow(NotFoundEntityException.class);
        assertThrows(NotFoundEntityException.class, () -> pasteBoxService.getByHash("sfwefwfwe"));
    }

    //как объект прошел по сервису
    @Test
    public void getExistHash() {
        PasteBox pasteBox = new PasteBox();
        pasteBox.setHash("1");
        pasteBox.setData("11");
        pasteBox.setPublic(true);

        when(pasteBoxRepository.getByHash("1")).thenReturn(pasteBox);

        PasteBoxResponse expected = new PasteBoxResponse("11", true);
        PasteBoxResponse actual = pasteBoxService.getByHash("1");

        assertEquals(expected, actual);
    }
}
