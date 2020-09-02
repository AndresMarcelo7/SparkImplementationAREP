let btn = document.getElementById('buttonB');
btn.addEventListener('click',() =>{
    let numbers = "kk";
	var deleteFoodConfig = { headers: { 'Content-Type': 'text/html'} };  
    var reqFoodId = document.getElementById('fname');
	axios.post('https://marcelo-spark-arep.herokuapp.com/testPost', reqFoodId,deleteFoodConfig)
        .then(res => {
            console.log("succesfull");
			alert(res);
        })
        .catch( error =>{
            console.log("Intentalo de nuevo");
        })

});
