import React, {Component} from 'react';
import {Link, withRouter} from 'react-router-dom';
import {Button, Container, Form, FormGroup, Input, Label} from 'reactstrap';
import AppNavbar from './AppNavbar';
import Dropdown from 'react-dropdown'
import 'react-dropdown/style.css'

class ProductEdit extends Component {

    emptyItem = {
        name: '',
        category: '',
        productId: '',
        price: '0.0',
    };

    constructor(props) {
        super(props);
        this.state = {
            item: this.emptyItem,
            categories: [],
            defaultCategory: 'N/A',
            selectedCurrency: 'EUR',
            convertedPrice: this.emptyItem.price
        };
        this.handleChange = this.handleChange.bind(this);
        this.handleCategoryChange = this.handleCategoryChange.bind(this);
        this.handleCurrencyChange = this.handleCurrencyChange.bind(this);
        this.handleSubmit = this.handleSubmit.bind(this);
    }

    async componentDidMount() {
        if (this.props.match.params.id !== 'new') {
            const product = await (await fetch(`/product/${this.props.match.params.id}`)).json();
            this.setState({item: product});
        }

        fetch('/categories')
            .then(response => response.json())
            .then(data => this.setState({categories: data}));
    }

    handleChange(event) {
        const target = event.target;
        const value = target.value;
        const name = target.name;
        let item = {...this.state.item};
        item[name] = value;
        this.setState({item});
    }

    handleCategoryChange(event) {
        const value = event.value;
        let item = {...this.state.item};
        item["category"] = this.state.categories.find(c => c.name === value);
        this.setState({item});
    }

    handleCurrencyChange(event) {
        const selectedCurrency = event.value;
        this.setState({selectedCurrency});
    }

    async handleSubmit(event) {
        event.preventDefault();
        let price = this.state.item.price;
        let selectedCurrency = this.state.selectedCurrency;

        await fetch(`/convert/${price}/${selectedCurrency}`)
            .then(response => response.json())
            .then(data => this.setState({convertedPrice: data}));

        this.state.item.price = this.state.convertedPrice;
        const {item} = this.state;
        item.category = (item.category) || this.state.categories.find(c => c.name === this.state.defaultCategory);

        await fetch((item.id === undefined) ? '/product' : '/product/' + item.id, {
            method: (item.id) ? 'PUT' : 'POST',
            headers: {
                'Accept': 'application/json',
                'Content-Type': 'application/json'
            },
            body: JSON.stringify(item),
        });
        this.props.history.push('/products');
    }

    render() {
        const {item} = this.state;
        const {categories} = this.state;
        const {selectedCurrency} = this.state;
        const title = <h2>{item.id ? 'Edit Product' : 'Add Product'}</h2>;
        const currencies = ['EUR', 'JPY', 'UAH', 'PLN', 'USD', 'GBP'];

        return <div>
            <AppNavbar/>
            <Container>
                {title}
                <Form onSubmit={this.handleSubmit}>
                    <FormGroup>
                        <Label for="name">Name</Label>
                        <Input required type="text" name="name" id="name" value={item.name || ''}
                               onChange={this.handleChange} autoComplete="productName"/>
                    </FormGroup>
                    <FormGroup>
                        <Label for="address">Category</Label>
                        <Dropdown required id="category" options={categories.map(p => p.name)}
                                  onChange={this.handleCategoryChange} value={item.category.name}
                                  placeholder="Select a category"/>
                    </FormGroup>
                    <FormGroup>
                        <Label for="productId">Id</Label>
                        <Input required type="number" min="0" name="productId" id="productId"
                               value={item.productId || ''}
                               onChange={this.handleChange} autoComplete="productId"/>
                    </FormGroup>

                    <div className="row">
                        <FormGroup className="col-md-3 mb-3">
                            <Label for="price">Price</Label>
                            <Input required type="number" min="0" step="any" name="price" id="price"
                                   value={item.price}
                                   onChange={this.handleChange} autoComplete="price"/>
                        </FormGroup>

                        <FormGroup className="col-md-3 mb-3">
                            <Label for="currency">Currency</Label>
                            <Dropdown required id="currency" options={currencies}
                                      onChange={this.handleCurrencyChange} value={selectedCurrency}/>
                        </FormGroup>
                    </div>
                    <FormGroup>
                        <Button color="primary" type="submit">Save</Button>{' '}
                        <Button color="secondary" tag={Link} to="/products">Cancel</Button>
                    </FormGroup>
                </Form>
            </Container>
        </div>
    }
}

export default withRouter(ProductEdit);