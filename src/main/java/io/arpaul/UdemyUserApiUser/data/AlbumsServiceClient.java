package io.arpaul.UdemyUserApiUser.data;

import java.util.ArrayList;
import java.util.List;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import io.arpaul.UdemyUserApiUser.ui.model.AlbumResponseModel;

@FeignClient(name="udemy-albums", fallback = AlbumsFallback.class)
public interface AlbumsServiceClient {
	@GetMapping("/users/{id}/albums")
	public List<AlbumResponseModel> getAlbums(@PathVariable String id);
}

@Component
class AlbumsFallback implements AlbumsServiceClient {

	@Override
	public List<AlbumResponseModel> getAlbums(String id) {
		return new ArrayList<>();
	}
	
}