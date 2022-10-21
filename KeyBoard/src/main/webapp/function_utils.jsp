<div class="alert"></div>

<script>
    const divMessage = document.querySelector(".alert");

    function messageError(msg){
        const message = document.createElement("div");
        message.classList.add("message");
        message.innerText = msg;
        divMessage.appendChild(message);

        setTimeout(()=> {
                message.style.display = "none";
        }, 5000);
    }
</script>