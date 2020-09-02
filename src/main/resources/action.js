let btn = document.getElementById('buttonB');
btn.addEventListener('click',() =>{
    let numbers = "kk";
	var deleteFoodConfig = { headers: { 'Content-Type': 'text/html'} };  
    var reqFoodId = document.getElementById('fname').value;
	axios.post('http://localhost:36000/testPost', reqFoodId,deleteFoodConfig)
        .then(res => {
            console.log(res);
			alert(res.data);
        })
        .catch( error =>{
            console.log("Intentalo de nuevo");
        })

});
