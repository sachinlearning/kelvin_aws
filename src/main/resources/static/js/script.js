$(document).ready(
		function() {
			$(".loader").hide();
			$('#fileUpload').val('');
			var proceedBtn = document.getElementById('proceedBtn');
			$(proceedBtn).click(
					function(event) {
						//stop submit the form, we will post it manually.
						event.preventDefault();
						$(".loader").show();
						$('button').prop('disabled',true);
						$('a').bind('click', function(e) {
							e.preventDefault();
						});
						if ($('#fileUpload')[0].files.length == 0) {
							$(".loader").hide();
							$('button').prop('disabled',false);
							$('a').unbind('click');
							$(".uploadStatus").css("color", "red");
							$(".uploadStatus").show().text(
									'Please upload an excel file.');
							return false;
						} else {
							var extstr = $('#fileUpload')[0].files[0].name
									.split('.');
							var ext = extstr[extstr.length - 1];
							if (ext == "xlsx" || ext == "xls") {
								
							} else {
								$(".loader").hide();
								$('button').prop('disabled',false);
								$('a').unbind('click');
								$(".uploadStatus").css("color", "red");
								$(".uploadStatus").text(
										'Please upload an excel file.');
								return false;
							}
						}

						if (window.FormData !== undefined) {
							var formData = new FormData();
							var file = $('#fileUpload')[0].files[0];
							formData.append("file", file);
							$.ajax({
								url : 'api/upload',
								type : "POST",
								enctype : "multipart/form-data",
								contentType : false,
								processData : false, 
								data : formData,
								cache : false,
								timeout : 600000,
								success : function(data) {
									$(".loader").hide();
									$('button').prop('disabled',false);
									$('a').unbind('click');
									if(data.responseText=='User has not logged in')
										window.location.href = "login";
									else if(data.responseText=='No Data Found'){
										$(".uploadStatus").css("color", "red");
										$(".uploadStatus").text("No data found to render the chart");
									}
									else{
									localStorage.setItem("data",JSON.stringify(data));
									window.location.href = "chart";
									}
								},
								error : function(err) {
									$(".loader").hide();
									$('button').prop('disabled',false);
									$('a').unbind('click');
									if(err.responseText=='User has not logged in')
										window.location.href = "login";
									else if(err.responseText=='No Data Found'){
										$(".uploadStatus").css("color", "red");
										$(".uploadStatus").text("No data found to render the chart");
									}
									else{
									$(".uploadStatus").css("color", "red");
									$(".uploadStatus").text(err.responseText);
									}
								}
							});
						} else {
							//alert("FormData is not supported.");
						}

					});

			
			$("#fileUpload").on('click', function () {
			    if(!$(this).val()){
			        //Initial Case when no document has been uploaded
			        $("#fileUpload").change(function(){
			        	var _this = this;
						if (this.files && this.files[0]) {
							$(".uploadStatus").css("color", "green");
							$(".uploadStatus").text(
									this.files[0].name);
							$('.image-title').html(this.files[0].name);
						} 
			        });
			    }else{
			        //Subsequent cases when the exact same document will be uploaded several times
			        $(this).val('');
			        $("#fileUpload").unbind('change');
			        $("#fileUpload").change(function(){
			        	var _this = this;
						if (this.files && this.files[0]) {
							$(".uploadStatus").css("color", "green");
							$(".uploadStatus").text(
									this.files[0].name);
							$('.image-title').html(this.files[0].name);
						}
			        });
			    }
			});

		});
