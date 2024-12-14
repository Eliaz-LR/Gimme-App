document.addEventListener("DOMContentLoaded", function () {
    const deleteBtn = document.getElementById("delete-profile-btn");

    if (deleteBtn) {
        deleteBtn.addEventListener("click", function () {
            const username = deleteBtn.getAttribute("data-username");
            const url = `/customers/${username}`;

            fetch(url, {
                method: 'DELETE',
                headers: {
                    'Content-Type': 'application/json',
                }
            })
                .then(response => {
                    if (response.status === 204) {
                        window.location.href = "/login";
                    } else {
                        response.json().then(data => {
                            alert("Erreur lors de la suppression : " + data.message);
                        });
                    }
                })
                .catch(error => {
                    console.error("Erreur lors de la suppression du profil :", error);
                    alert("Une erreur est survenue lors de la suppression de votre profil.");
                });
        });
    }
});
