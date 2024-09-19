package es.rufflecol.sam.mtgcards.domain

import es.rufflecol.sam.mtgcards.data.remote.dto.MtgCardDto
import es.rufflecol.sam.mtgcards.domain.mapper.MtgCardMapper
import junit.framework.TestCase.assertEquals
import org.junit.Test

class MtgCardMapperTest {

    private val mtgCardMapper = MtgCardMapper()

    @Test
    fun `mapSimple should correctly map DTO to SimpleMtgCard`() {
        // Given
        val dto = MtgCardDto(
            id = "123",
            name = "Test Card",
            imageUrl = "http://example.com/image.png",
            manaCost = "3R",
            power = 3,
            toughness = 2,
            type = "Creature",
            artist = "John Doe",
            text = "text"
        )

        // When
        val result = mtgCardMapper.mapSimple(dto)

        // Then
        assertEquals("123", result.id)
        assertEquals("Test Card", result.name)
        assertEquals("https://example.com/image.png", result.imageUrl)
        assertEquals("3R", result.manaCost)
        assertEquals("3/2", result.powerAndToughness)
    }

    @Test
    fun `mapSimple should handle empty or null fields`() {
        // Given
        val dto = MtgCardDto(
            id = null,
            name = null,
            imageUrl = null,
            manaCost = null,
            power = null,
            toughness = null,
            type = null,
            artist = null,
            text = null,
        )

        // When
        val result = mtgCardMapper.mapSimple(dto)

        // Then
        assertEquals("", result.id)
        assertEquals("", result.name)
        assertEquals("", result.imageUrl)  // Should be an empty string after mapImageUrl
        assertEquals("", result.manaCost)
        assertEquals("", result.powerAndToughness)  // Should be empty string due to null power/toughness
    }

    @Test
    fun `mapDetailed should correctly map DTO to DetailedMtgCard`() {
        // Given
        val dto = MtgCardDto(
            id = "123",
            name = "Test Card",
            imageUrl = "http://example.com/image.png",
            manaCost = "3R",
            power = 3,
            toughness = 2,
            type = "Creature",
            artist = "John Doe",
            text = "text"
        )

        // When
        val result = mtgCardMapper.mapDetailed(dto)

        // Then
        assertEquals("123", result.id)
        assertEquals("Test Card", result.name)
        assertEquals("https://example.com/image.png", result.imageUrl)
        assertEquals("3R", result.manaCost)
        assertEquals("3/2", result.powerAndToughness)
        assertEquals("Creature", result.type)
        assertEquals("John Doe", result.artist)
    }

    @Test
    fun `mapDetailed should handle empty or null fields`() {
        // Given
        val dto = MtgCardDto(
            id = null,
            name = null,
            imageUrl = null,
            manaCost = null,
            power = null,
            toughness = null,
            type = null,
            artist = null,
            text = null,
        )

        // When
        val result = mtgCardMapper.mapDetailed(dto)

        // Then
        assertEquals("", result.id)
        assertEquals("", result.name)
        assertEquals("", result.imageUrl)  // Should be an empty string after mapImageUrl
        assertEquals("", result.manaCost)
        assertEquals("", result.powerAndToughness)  // Should be empty string due to null power/toughness
        assertEquals("", result.type)
        assertEquals("", result.artist)
    }
}