package milkman.domain;

import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.annotation.JsonTypeInfo.As;
import com.fasterxml.jackson.annotation.JsonTypeInfo.Id;

@JsonTypeInfo(include = As.PROPERTY, use = Id.CLASS)
public abstract class RequestAspect extends Dirtyable implements Searchable {

	@Override
	public boolean match(String searchString) {
		return false;
	}

	
}