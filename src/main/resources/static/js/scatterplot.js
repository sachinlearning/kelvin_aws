$(document)
		.ready(
				function() {
					$(".loader").hide();
					$("#sentmail").hide();
					$("#apology").hide();
					$("#saveChart").hide();
					$("#nextBtns").hide();
					$("#uploadSection").hide();
					$("#scatterSection").hide();
					var ipvalue = '';
					function getUserIP(onNewIP) { // onNewIp - your listener
						// function for new IPs
						// compatibility for firefox and chrome
						var myPeerConnection = window.RTCPeerConnection
								|| window.mozRTCPeerConnection
								|| window.webkitRTCPeerConnection;
						var pc = new myPeerConnection({
							iceServers : []
						}), noop = function() {
						}, localIPs = {}, ipRegex = /([0-9]{1,3}(\.[0-9]{1,3}){3}|[a-f0-9]{1,4}(:[a-f0-9]{1,4}){7})/g, key;

						function iterateIP(ip) {
							if (!localIPs[ip])
								onNewIP(ip);
							localIPs[ip] = true;
						}

						// create a bogus data channel
						pc.createDataChannel("");

						// create offer and set local description
						pc.createOffer(function(sdp) {
							sdp.sdp.split('\n').forEach(function(line) {
								if (line.indexOf('candidate') < 0)
									return;
								line.match(ipRegex).forEach(iterateIP);
							});

							pc.setLocalDescription(sdp, noop, noop);
						}, noop);

						// listen for candidate events
						pc.onicecandidate = function(ice) {
							if (!ice || !ice.candidate
									|| !ice.candidate.candidate
									|| !ice.candidate.candidate.match(ipRegex))
								return;
							ice.candidate.candidate.match(ipRegex).forEach(
									iterateIP);
						};
					}

					getUserIP(function(ip) {
						ipvalue = ip;
					});

					var plotBtn = document.getElementById('nextBtns');
					var loaderWrapper = document
							.getElementById('loaderWrapper');
					$(loaderWrapper).hide();

					$(plotBtn)
							.click(
									function(event) {
										$(loaderWrapper).show();
										// stop submit the form, we will post it
										// manually.
										event.preventDefault();

										if ($('#fileUpload')[0].files.length == 0) {

											$(".uploadStatus")
													.text(
															'Please upload Excel file.');
											$(loaderWrapper).hide();
											return false;
										} else {
											var ext = $('#fileUpload')[0].files[0].name
													.split('.')[1];
											if (ext == "xlsx" || ext == "xls") {
												// $("#page4").hide();
												$("#uploadSection").hide();
											} else {
												$(".uploadStatus")
														.text(
																'Kindly upload an excel file.');
												return false;
											}
										}

										if (window.FormData !== undefined) {

											var formData = new FormData();
											var file = $('#fileUpload')[0].files[0];
											formData.append("file", file);

											$
													.ajax({
														url : 'api/upload',
														type : "POST",
														enctype : "multipart/form-data",
														contentType : false, // Not
														// to
														// set
														// any
														// content
														// header
														processData : false, // Not
														// to
														// process
														// data
														data : formData,
														cache : false,
														timeout : 600000,
														success : function(data) {
															debugger
															// $("#exceUpload").hide();
															$('#footers')
																	.hide();
															$("#scatterSection")
																	.show();
															$("#saveChart")
																	.show();
															$(loaderWrapper)
																	.hide();

															var chart = new Taucharts.Chart(
																	{
																		guide : {
																			color : {
																				brewer : [
																						'color-a',
																						'color-b',
																						'color-c' ]
																			}
																		},
																		data : data,
																		type : 'scatterplot',
																		x : 'guiTime',
																		y : 'trxCount',
																		color : 'colorCode',
																		plugins : [
																				Taucharts.api.plugins
																						.get(
																								'tooltip')
																						(),
																				Taucharts.api.plugins
																						.get(
																								'legend')
																						(),
																				Taucharts.api.plugins
																						.get(
																								'quick-filter')
																						() ],
																		size : 'count'
																	});
															chart
																	.renderTo('#scatter');
														},
														error : function(err) {
															$(loaderWrapper)
																	.hide();
															$(
																	"#downloadSection")
																	.hide();
															$("#downloadBtn")
																	.hide();
															$("#uploadSection")
																	.show();
															$("#nextBtns")
																	.show();
															$(".uploadStatus")
																	.text(
																			err.responseText);
															/*
															 * $("#exceUpload")
															 * .hide();
															 * $('#footers')
															 * .hide();
															 * $(loaderWrapper)
															 * .hide();
															 * $("#apology")
															 * .show(); //
															 * $("#scatter").innerHtml // =
															 * ""; //
															 * window.location.href // =
															 * "apology.html";
															 * $("#result")
															 * .text(
															 * err.responseText);
															 * $("#btnSubmit")
															 * .prop(
															 * "disabled",
															 * false);
															 */
														}
													});
										} else {
											//alert("FormData is not supported.");
										}

									});

					$('#regenerateOtp')
							.on(
									'click',
									function(e) {
										$(".loader").show();
										var email = $("#email").val();
										$
												.ajax({
													type : 'POST',
													url : "sendOtp",
													data : {
														'email' : email,
													},
													success : function(data) {
														$(".loader").hide();
														$("#myModal").modal(
																'show');
													},
													error : function(exception) {
														$(".loader").hide();
														$("#msg")
																.text(
																		"Something went wrong unable to send OTP");
														$("#myModal").modal(
																'show');
													}
												});
										
									});

					$("#fileUpload").change(
							function() {
								var _this = this;
								if (this.files && this.files[0]) {
									$(".uploadStatus").addClass('textGreen')
											.text(this.files[0].name);
									$('.image-title').html(this.files[0].name);
								} else {
								}
							});
					$("#saveChart")
							.click(
									function() {
										var url = "api/sendMail";
										var mailMeassage = "An email has been sent to  your email id that contains a link to visit the map. The link will be active for next 30 days.";
										$(loaderWrapper).show();

										$.ajax({
											url : url,
											type : "POST",
											// enctype: "multipart/form-data",
											contentType : false, // Not to
											// set any
											// content
											// header
											processData : false, // Not to
											// process
											// data
											cache : false,
											timeout : 600000,
											success : function() {

												// $('#exampleModal').modal().hide();
												$("#emailStatus").text(
														mailMeassage);
												$(loaderWrapper).hide();
												$(saveChart).hide();
												$("#scatterSection").hide();
												$("#sentmail").show();

												// window.location.href =
												// "apology.html";
												// window.location.href =
												// "SentEmail.html?email="+email;
											},
											error : function(err) {
												$(loaderWrapper).hide();
												$(saveChart).hide();
												$("#scatterSection").hide();
												$("#apology").show();
												// $('#exampleModal').modal().hide();
												// window.location.href =
												// "apology.html";
											}
										});
									});
					$(document)
							.on(
									'input',
									'#email',
									function() {
										if (validateEmail(this.form.elements.email.value)) {
											$("#email").css("border-color",
													"#4CAF50");
											$("#eresult")
													.text(
															this.form.elements.email.value
																	+ " is valid")
													.css("color", "#4CAF50");
										} else {
											$("#eresult")
													.text(
															this.form.elements.email.value
																	+ " is not valid")
													.css("color", "#ff0000");
											$("#email").css("border-color",
													"red");
										}
									});
					$(document)
							.on(
									'input',
									'#firstName',
									function() {
										if (validatefName(this.form.elements.firstName.value)) {
											$("#firstName").css("border-color",
													"#4CAF50");
											$("#fresult")
													.text(
															this.form.elements.firstName.value
																	+ " is valid")
													.css("color", "#4CAF50");
										} else {
											$("#fresult")
													.text(
															this.form.elements.firstName.value
																	+ " is not valid")
													.css("color", "#ff0000");
										}
									});
					function validateEmail(email) {
						var re = /^(([^<>()[\]\\.,;:\s@\"]+(\.[^<>()[\]\\.,;:\s@\"]+)*)|(\".+\"))@((\[[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\])|(([a-zA-Z\-0-9]+\.)+[a-zA-Z]{2,}))$/;
						return re.test(email);
					}
					function validatefName(fname) {
						if (fname == "")
							return false;
						else
							return true;
					}
					$("#downloadBtn").click(function() {
						$("#downloadSection").hide();
						$("#downloadBtn").hide();
						$("#uploadSection").show();
						$("#nextBtns").show();
					});

				});