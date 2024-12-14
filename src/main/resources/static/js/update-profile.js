document.getElementById('submit-button').addEventListener('click', function (event) {
    event.preventDefault();

    const username = document.getElementById('username').value;
    const name = document.getElementById('name').value;
    const password = document.getElementById('password').value;

    const updatedProfile = {
        name: name,
        password: password
    };

    fetch(`/customers/${username}`, {
        method: 'PUT',
        headers: {
            'Content-Type': 'application/json'
        },
        body: JSON.stringify(updatedProfile)
    })
        .then(response => {
            if (response.ok) {
                const successDiv = document.getElementById("success-message");
                const successText = document.getElementById("success-text");
                successText.textContent = "Profil mis à jour avec succès"
                successDiv.style.display = "block";
            } else {
                throw new Error('Erreur lors de la mise à jour du profil');
            }
        })
        .catch(error => {
            alert(error.message);
        });
});
