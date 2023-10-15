package com.example.demo;

import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

import static org.mockito.Mockito.*;

@SpringBootTest
public class VolumeTest {

    @Mock
    private AudioManager audioManager; // 1 - Crie um mock da classe AudioManager

    @InjectMocks
    private VolumeUtil volumeUtil; // 2 - Injete um mock da classe VolumeUtil

    @Test
    void testAudioManagerSetVolume() {
        // 3 - Realize uma chamada para o método maximizeVolume do objeto VolumeUtil
        volumeUtil.maximizeVolume(100);

        // 4 - Verifique se o método setVolume foi chamado uma única vez
        verify(audioManager, times(1)).setVolume(anyInt());
    }
}

