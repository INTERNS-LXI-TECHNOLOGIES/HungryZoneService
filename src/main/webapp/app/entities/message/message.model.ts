import dayjs from 'dayjs/esm';
import { IOrder } from 'app/entities/order/order.model';

export interface IMessage {
  id: number;
  userLogin?: string | null;
  chatDate?: dayjs.Dayjs | null;
  textMessage?: string | null;
  user?: Pick<IOrder, 'id'> | null;
}

export type NewMessage = Omit<IMessage, 'id'> & { id: null };
