document.addEventListener('DOMContentLoaded', () => {
    const forms = document.querySelectorAll('.favorite-form');

    forms.forEach(form => {
        form.addEventListener('submit', async (event) => {
            event.preventDefault();

            const actionUrl = form.getAttribute('action');
            const isAddFavorite = form.querySelector('button').textContent === "Ajouter aux favoris";
            const method = isAddFavorite ? 'PUT' : 'DELETE';

            try {
                const response = await fetch(actionUrl, {
                    method: method,
                    headers: {
                        'Content-Type': 'application/json',
                    },
                });

                if (response.ok) {
                    // Vous pouvez recharger ou mettre à jour l'interface selon la réponse
                    location.reload();
                } else {
                    console.error('Erreur lors de l\'ajout/suppression aux favoris');
                    alert('Erreur lors de la mise à jour des favoris');
                }
            } catch (error) {
                console.error('Erreur lors de l\'ajout/suppression aux favoris:', error);
                alert('Impossible de mettre à jour les favoris. Veuillez réessayer.');
            }
        });
    });
});

document.addEventListener('DOMContentLoaded', () => {
    const forms = document.querySelectorAll('.purchase-form');

    forms.forEach(form => {
        form.addEventListener('submit', async (event) => {
            event.preventDefault();

            const actionUrl = form.getAttribute('action');

            try {
                const response = await fetch(actionUrl, {
                    method: 'POST',
                    headers: {
                        'Content-Type': 'application/json',
                    },
                });
                if (response.ok) {
                    location.reload();
                }
            } catch (error) {
                console.error('Erreur lors de l\'achat:', error);
                alert('Erreur lors de l\'achat.');
            }
        });
    });
});