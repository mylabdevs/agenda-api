package com.agenda.api.controller.response;

import java.util.ArrayList;
import java.util.List;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class ResponseList<T> {

	private List<T> data;
	private List<String> errors;

	public void addErros(String error) {
		if (this.errors == null) {
			this.errors = new ArrayList<String>();
		}

		this.errors.add(error);
	}

	public void addData(T data) {
		if (this.data == null) {
			this.data = new ArrayList<T>();
		}

		this.data.add(data);
	}

}
