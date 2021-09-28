$(document)
		.ready(
				function() {
					"use strict";

					$(".input100").val("");

					/*==================================================================
					[ Focus Contact2 ]*/
					$('.input100').each(function() {
						$(this).on('blur', function() {
							if ($(this).val().trim() != "") {
								$(this).addClass('has-val');
							} else {
								$(this).removeClass('has-val');
							}
						})
						if ($(this).val().trim() != "") {
							$(this).addClass('has-val');
						}
					})

					/*==================================================================
					[ Validate ]*/
					var input = $('.validate-input .input100');

					$('.validate-form').on('submit', function() {
						var check = true;

						for (var i = 0; i < input.length; i++) {
							if (validate(input[i]) == false) {
								showValidate(input[i]);
								check = false;
							}
						}

						return check;
					});

					$('.validate-form .input100').each(function() {
						$(this).focus(function() {
							hideValidate(this);
						});
					});

					function validate(input) {
						if ($(input).attr('type') == 'email'
								|| $(input).attr('name') == 'email') {
							if ($(input)
									.val()
									.trim()
									.match(
											/^([a-zA-Z0-9_\-\.]+)@((\[[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\.)|(([a-zA-Z0-9\-]+\.)+))([a-zA-Z]{1,5}|[0-9]{1,3})(\]?)$/) == null
									|| $(input).val().trim().length < 6
									|| $(input).val().trim().length > 45) {
								return false;
							}
						}

						else if ($(input).attr('name') == 'firstName'
								|| $(input).attr('name') == 'lastName') {
							if ($(input).val().trim().match(/^[a-zA-Z]{3,30}$/) == null) {
								return false;
							}
						}

						else if ($(input).attr('name') == 'password') {
							if ($(input)
									.val()
									.trim()
									.match(
											/^(?=.*[a-z])(?=.*[A-Z])(?=.*[0-9])(?=.*[.!@#\$%\^&\*]).{8,15}$/) == null) {
								return false;
							}
						} else if ($(input).attr('name') == 'confirmPassword') {
							if ($("#password").val().trim() != $(
									"#confirmPassword").val().trim()) {
								return false;
							}
						} else if ($(input).attr('name') == 'company') {
							//            if($(input).val().trim().match(/^[a-zA-Z0-9&]*$/) == null|| $(input).val().trim().length<3 || $(input).val().trim().length>40) {
							if ($(input).val().trim().match(
									/^[a-zA-Z0-9]([a-zA-Z0-9]|[- @\.#&!])*$/) == null
									|| $(input).val().trim().length < 3
									|| $(input).val().trim().length > 40) {
								return false;
							}
						} else {
							if ($(input).val().trim() == '') {
								return false;
							}
						}
					}

					function showValidate(input) {
						var thisAlert = $(input).parent();

						$(thisAlert).addClass('alert-validate');
					}

					function hideValidate(input) {
						var thisAlert = $(input).parent();

						$(thisAlert).removeClass('alert-validate');
					}

				})(jQuery);