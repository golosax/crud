import React, {Component} from 'react';
import {Button, ButtonGroup, Container, Table} from 'reactstrap';
import AppNavbar from './AppNavbar';
import {Link} from 'react-router-dom';
import { confirmAlert } from 'react-confirm-alert';
import 'react-confirm-alert/src/react-confirm-alert.css';

class ProductList extends Component {

    constructor(props) {
        super(props);
        this.state = {products: [], isLoading: true};
        this.remove = this.remove.bind(this);
    }

    componentDidMount() {
        this.setState({isLoading: true});

        fetch('/products')
            .then(response => response.json())
            .then(data => this.setState({products: data, isLoading: false}));
    }

    async remove(id) {
        await fetch(`/product/${id}`, {
            method: 'DELETE',
            headers: {
                'Accept': 'application/json',
                'Content-Type': 'application/json'
            }
        }).then(() => {
            let updatedProducts = [...this.state.products].filter(i => i.id !== id);
            this.setState({products: updatedProducts});
        });
    }

    async confirmDeletion(id, name) {
        confirmAlert({
            title: 'Delete product ' + name,
            message: 'Are you sure to do this?',
            buttons: [
                {
                    label: 'Yes',
                    onClick: () => this.remove(id)
                },
                {
                    label: 'No',
                    onClick: () => window.close()
                }
            ]
        });
    };

    render() {
        const {products, isLoading} = this.state;

        if (isLoading) {
            return <p>Loading...</p>;
        }

        const productList = products.map(product => {
            return <tr key={product.id}>
                <td style={{whiteSpace: 'nowrap'}}>{product.name}</td>
                <td>{product.productId}</td>
                <td>{product.category.name}</td>
                <td>€ {product.price}</td>
                <td>
                    <ButtonGroup>
                        <Button size="sm" color="primary" tag={Link} to={"/product/" + product.id}>Edit</Button>
                        <Button size="sm" color="danger" onClick={() => this.confirmDeletion(product.id, product.name)}>Delete</Button>
                    </ButtonGroup>
                </td>
            </tr>
        });

        return (
            <div>
                <AppNavbar/>
                <Container fluid>
                    <div className="float-right">
                        <Button color="success" tag={Link} to="/product/new">Add Product</Button>
                    </div>
                    <h3>Products</h3>
                    <Table className="mt-5">
                        <thead>
                        <tr>
                            <th width="20%">Name</th>
                            <th width="20%">Id</th>
                            <th width="20%">Category</th>
                            <th width="10%">Price</th>
                            <th>Actions</th>
                        </tr>
                        </thead>
                        <tbody>
                        {productList}
                        </tbody>
                    </Table>
                </Container>
            </div>
        );
    }
}

export default ProductList;