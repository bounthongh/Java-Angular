import { User } from './user-model';
import { Produit } from './produit-model';

export class Panier {

    id: number;
    quantite: number;
    id_user: User;
    id_produit: Produit;
}
