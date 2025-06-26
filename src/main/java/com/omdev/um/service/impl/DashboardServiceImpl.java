package com.omdev.um.service.impl;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.omdev.um.dto.QuoteApiResponseDTO;
import com.omdev.um.service.DashboardService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
public class DashboardServiceImpl implements DashboardService {
	
	@Value("${api.url}")
	private String url;

	private final RestTemplate restTemplate;
	
	@Override
	public QuoteApiResponseDTO getRandomQuote() {
		log.info("getRandomQuote called");
		
		ResponseEntity<QuoteApiResponseDTO> forEntity = restTemplate.getForEntity(url, QuoteApiResponseDTO.class);
		
		QuoteApiResponseDTO body = forEntity.getBody();
		
		log.info("getRandomQuote returning-- quote:{}",body.getAuthor());
		return body;
	}

}
