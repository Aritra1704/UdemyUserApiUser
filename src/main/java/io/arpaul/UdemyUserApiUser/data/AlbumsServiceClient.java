package io.arpaul.UdemyUserApiUser.data;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.openfeign.FallbackFactory;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import feign.FeignException;
import io.arpaul.UdemyUserApiUser.ui.model.AlbumResponseModel;

@FeignClient(name="udemy-albums", fallbackFactory = AlbumsFallbackFactory.class)
public interface AlbumsServiceClient {
	@GetMapping("/users/{id}/albums")
	public List<AlbumResponseModel> getAlbums(@PathVariable String id);
}

@Component
class AlbumsFallbackFactory implements FallbackFactory<AlbumsServiceClient> {

	@Override
	public AlbumsServiceClient create(Throwable cause) {
		return new ALbumsServiceClientFallback(cause);
	}
	
}

class ALbumsServiceClientFallback implements AlbumsServiceClient {

	Logger logger = LoggerFactory.getLogger(this.getClass());
	private final Throwable cause;
	
	public ALbumsServiceClientFallback(Throwable cause) {
		this.cause = cause;
	}
	
	@Override
	public List<AlbumResponseModel> getAlbums(String id) {
		if(cause instanceof FeignException && ((FeignException) cause).status() == 404) {
			logger.error("404 error took place when getAlbums was called with userId: "
					+id+", Error message: "
					+cause.getLocalizedMessage());
		} else {
			logger.error("Other error took place: "+cause.getLocalizedMessage());
		}
		return new ArrayList<>();
	}
	
}
//@Component
//class AlbumsFallback implements AlbumsServiceClient {
//
//	@Override
//	public List<AlbumResponseModel> getAlbums(String id) {
//		return new ArrayList<>();
//	}
//	
//}
