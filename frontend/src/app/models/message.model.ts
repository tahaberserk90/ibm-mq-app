export interface Message {
    id: string;
    content: string;
    timestamp: Date;
    status: string;
    // Add other relevant fields based on your MQ message structure
  }