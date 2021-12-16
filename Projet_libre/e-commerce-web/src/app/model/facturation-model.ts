import { Panier } from './panier-model';
import { Address } from './address-model';

export class Facturation {

    id: number;
    totalQt: number;
    totalPrix: number;
    payement: string;
    livraison: string;
    id_panier: Panier;
    id_address: Address;
}
