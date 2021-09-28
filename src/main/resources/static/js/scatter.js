$(document)
		.ready(
				function() {
					$(".loader").hide();
					var saveIndex = 0;
					var clickedOnSaveChart = 0;
					var nextPage = 0;
					$("#loaderWrapper").hide();
					function created3Chart(data) {
						$("#loaderWrapper").hide();
						var chart = new Taucharts.Chart(
								{
									guide : {
										x : {
											label : {
												text : "Time"
											}
										},
										y : {
											label : {
												text : "Count"
											}
										},
										color : {
											label : "Business Process Criticality Rating",
											brewer : [ 'color-a', 'color-b',
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
													.get('tooltip')
													(
															{
																fields : [
																		"trxCode",
																		"description",
																		"individualPercentageForTrxCount",
																		"cumlativeValueForTrxCount",
																		"individualPercentageForGuiTime",
																		"cumlativeValueForGuiTime",
																		"colorCode", ],
																formatters : {
																	trxCode : {
																		label : "Business Transaction Code"
																	},
																	description : {
																		label : "Business Process Description"
																	},
																	individualPercentageForTrxCount : {
																		label : "Individual Percentage Rating for the Count",
																		format : function(
																				n) {
																			return (n + "%");
																		}
																	},
																	cumlativeValueForTrxCount : {
																		label : "Cumulative Percentage Rating for the Count",
																		format : function(
																				n) {
																			return (n + "%");
																		}
																	},
																	individualPercentageForGuiTime : {
																		label : "Individual Percentage Rating for the Time",
																		format : function(
																				n) {
																			return (n + "%");
																		}
																	},
																	cumlativeValueForGuiTime : {
																		label : "Cumulative Percentage Rating for the Time",
																		format : function(
																				n) {
																			return (n + "%");
																		}
																	},
																	colorCode : {
																		label : "Business Criticality Rating"
																	}
																}
															}),
											Taucharts.api.plugins
													.get('legend')
													(
															{
																fields : [
																		"colorCode", ],
																formatters : {
																	colorCode : {
																		label : "Business Transaction Code"
																	}
																}

															}),

											Taucharts.api.plugins.get(
													'quick-filter')() ],
									size : null
								});
						// debugger;
						$('#scatter')[0].innerHTML = ''
						// $("#spanel").addClass('hideSpan');
						chart.renderTo('#scatter');
					}
					;
					created3Chart(JSON.parse(localStorage.getItem("data")));
					$(".tau-chart__filter__wrap:gt(1)").hide();
					/*
					 * $(".tau-chart__filter__wrap:eq(0)
					 * text:eq(0)").text("Max");
					 * $(".tau-chart__filter__wrap:eq(0)
					 * text:eq(1)").text("Min");
					 * $(".tau-chart__filter__wrap:eq(1)
					 * text:eq(0)").text("Max");
					 * $(".tau-chart__filter__wrap:eq(1)
					 * text:eq(1)").text("Min");
					 */
					$(".tau-chart__filter__wrap:eq(0) text:eq(0)").hide();
					$(".tau-chart__filter__wrap:eq(0) text:eq(1)").hide();
					$(".tau-chart__filter__wrap:eq(1) text:eq(0)").hide();
					$(".tau-chart__filter__wrap:eq(1) text:eq(1)").hide();
					$(".tau-chart__legend__title:eq(1)").text("Scale Count");
					$(".tau-chart__legend__title:eq(2)").text("Scale Time");

					$("#proceed")
							.click(
									function() {

										if (saveIndex > 0) {
											window.location
													.replace("https://www.thesiliconpartners.com/tspkelvin/additional-reports/");
										} else {
											$("#confirmationModal").modal(
													'show');
										}
									});

					$("#no")
							.click(
									function() {
										window.location
												.replace("https://www.thesiliconpartners.com/tspkelvin/additional-reports/");
									});
					$("#nextPage")
							.click(
									function() {
										if (saveIndex > 0 && nextPage > 0) {
											window.location
													.replace("https://www.thesiliconpartners.com/tspkelvin/additional-reports/");
										}
										// clickedOnSaveChart = 0;
									});

					// to save the chart
					$("#save")
							.click(
									function() {
										$(".loader").show();
										document.getElementById("save").disabled = true;
										var url = "api/sendMail";
										$
												.ajax({
													url : url,
													type : "POST",
													contentType : false, // Not
													// to
													processData : false, // Not
													// to
													cache : false,
													timeout : 600000,
													success : function(data) {
														$(".loader").hide();
														if (data == 'User has not logged in'
																|| data.responseText == 'User has not logged in')
															window.location.href = "login";
														else {
															$("#msg")
																	.text(
																			"An email has been sent successfully to the registered email id.");
															$("#myModal")
																	.modal(
																			'show');
															saveIndex = 1;
														}
														document
																.getElementById("save").disabled = false;
													},
													error : function(err) {
														$(".loader").hide();
														if (err.responseText == 'User has not logged in'
																|| err == 'User has not logged in')
															window.location.href = "login";
														else {
															$("#msg")
																	.text(
																			"Something went wrong unable to send email");
															$("#myModal")
																	.modal(
																			'show');
														}
														document
																.getElementById("save").disabled = false;
													}

												});
									});

					$("#saveChart")
							.click(
									function() {
										$(".loader").show();
										var url = "api/sendMail";
										nextPage = 1;
										$
												.ajax({
													url : url,
													type : "POST",
													contentType : false, // Not
													// to
													processData : false, // Not
													// to
													cache : false,
													timeout : 600000,
													success : function(data) {
														$(".loader").hide();
														if (data.responseText == 'User has not logged in'
																|| data == 'User has not logged in')
															window.location.href = "login";
														else {
															$("#msg")
																	.text(
																			"An email has been sent successfully to the registered email id.");
															$("#myModal")
																	.modal(
																			'show');
															saveIndex = 1;
														}
													},
													error : function(err) {
														$(".loader").hide();
														if (err.responseText == 'User has not logged in'
																|| err == 'User has not logged in')
															window.location.href = "login";
														else {
															$("#msg")
																	.text(
																			"Something went wrong unable to send email");
															$("#myModal")
																	.modal(
																			'show');
														}
													}

												});
									});

				});