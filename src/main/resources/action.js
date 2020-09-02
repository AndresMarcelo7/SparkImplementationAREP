let btn = document.getElementById('buttonB');
btn.addEventListener('click',() =>{
    /*let numbers = "kk";
	var deleteFoodConfig = { headers: { 'Content-Type': 'application/json'} };  
    var reqFoodId = document.getElementById('fname').value+"=hola";
	axios.post('http://localhost:36000/testPost', reqFoodId,deleteFoodConfig)
        .then(res => {
            console.log(res);
			alert(res.data);
        })
        .catch( error =>{
            console.log("Intentalo de nuevo");
        })
		*/
		 $.ajax({
          type: 'POST', url: 'https://marcelo-spark-arep.herokuapp.com/testPost', data: {
            email: "test@email.com"
          },
        }).done(function(res) {
          console.log(res);
        })

});
