package com.agenda.api.controller.response;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Response<T> {

	private T data;
	private List<String> errors;

	public void addErros(String error) {
		if (this.errors == null) {
			this.errors = new ArrayList<String>();
		}

		this.errors.add(error);
	}

}
