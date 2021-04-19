package com.seetharamanv.estores.preloads

import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule
import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import com.fasterxml.jackson.module.kotlin.readValue
import com.fasterxml.jackson.module.kotlin.registerKotlinModule
import com.seetharamanv.estores.entities.Item
import com.seetharamanv.estores.repositories.ItemsRepository
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Service
import javax.annotation.PostConstruct

@Service
class ItemsPreloadDataService(val itemsRepository: ItemsRepository) {
    @Value("\${json.config.folder}")
    private val pathToPreloadData: String = ""

    @PostConstruct
    private fun preloadData() {
        val mapper = jacksonObjectMapper()
        mapper.registerKotlinModule()
        mapper.registerModule(JavaTimeModule())
        val inputStream = javaClass.getResourceAsStream(pathToPreloadData)
        val jsonTextList: List<Item> = mapper.readValue(inputStream)
        itemsRepository.saveAll(jsonTextList)
    }
}