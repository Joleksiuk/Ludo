import {
  Button,
  Card,
  CardActions,
  CardContent,
  CardHeader,
  Grid,
  Paper,
  Typography,
} from "@mui/material";
import React from "react";

interface HomePageCardProps {
    title: string,
  children?: JSX.Element;
  buttonText: string;
  href: string
}

export default function HomePageCard(props: HomePageCardProps) {
  return (
    <Card>
        <CardHeader title={props.title}>
        </CardHeader>
        <CardContent>
            {props.children}
        </CardContent>
        <CardActions>
          <Button href={props.href}>
            {props.buttonText}
          </Button>
        </CardActions>
    </Card>
  );
}
