import { User } from './user-model';

export class Produit {
    id: number;
    reference: string;
    prix: number;
    nom: string;
    description: string;
    stock: number;
    url: string;
    id_user: User;
}
