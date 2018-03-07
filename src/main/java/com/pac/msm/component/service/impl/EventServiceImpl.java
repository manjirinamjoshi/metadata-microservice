package com.pac.msm.component.service.impl;

import org.springframework.data.cassandra.repository.MapId;
import org.springframework.data.cassandra.repository.support.BasicMapId;
import org.springframework.stereotype.Service;

import com.googlecode.jsonrpc4j.spring.AutoJsonRpcServiceImpl;
import com.pac.lib.core.common.PacException;
import com.pac.lib.core.context.RequestContext;
import com.pac.msm.component.domain.EventMetadata;
import com.pac.msm.component.repository.EventMetadataRepository;
import com.pac.msm.component.service.EventService;

@Service
@AutoJsonRpcServiceImpl
public class EventServiceImpl implements EventService {
	
	private final EventMetadataRepository eventMetadataRepository;
	
	public EventServiceImpl(EventMetadataRepository eventMetadataRepository) {
        this.eventMetadataRepository = eventMetadataRepository;
    }
	
	@Override
	public EventMetadata getEventMetadata(RequestContext requestContext,
			String key) throws PacException {
		MapId primaryKey = BasicMapId.id("id", key);
		return eventMetadataRepository.findOne(primaryKey);
	}

	@Override
	public void insertEventMetadata(RequestContext requestContext, EventMetadata eventMetadata) throws PacException {
		eventMetadataRepository.save(eventMetadata);
	}
	
	@Override
    public int multiplier(int a, int b) {
        return a * b;
    }

}
